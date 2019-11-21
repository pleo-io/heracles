import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Money from '@material-ui/icons/Money';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://material-ui.com/">
        Heracles
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const useStyles = makeStyles(theme => ({
  '@global': {
    body: {
      backgroundColor: theme.palette.common.white,
    },
  },
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function FormatAmountForm() {
  const classes = useStyles();

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>

        <Avatar className={classes.avatar}>
          <Money />
        </Avatar>

        <Typography component="h1" variant="h5">
          Format Amount
        </Typography>

        <form className={classes.form} noValidate>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="currency"
            label="currency"
            name="email"
            type="text"
            autoComplete=""
            autoFocus
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="value"
            label="value"
            type="text"
            id="value"
            autoComplete=""
          />
          <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="precision"
              label="precision"
              type="text"
              id="precision"
              autoComplete=""
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Format
          </Button>

          {/*<Grid container>*/}
          {/*  <Grid item xs>*/}
          {/*    <Link href="#" variant="body2">*/}
          {/*      Forgot password?*/}
          {/*    </Link>*/}
          {/*  </Grid>*/}
          {/*  <Grid item>*/}
          {/*    <Link href="#" variant="body2">*/}
          {/*      {"Don't have an account? Sign Up"}*/}
          {/*    </Link>*/}
          {/*  </Grid>*/}
          {/*</Grid>*/}
        </form>
      </div>

      <Box mt={8}>
        <Copyright />
      </Box>
    </Container>
  );
}
